const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const userModel = require('../models/userModel');
const response = require('../utils/response');

const generateToken = (payload) => {
    return jwt.sign(payload, process.env.JWT_SECRET, {
        expiresIn: process.env.JWT_EXPIRES_IN
    });
};

exports.login = async (req, res) => {
    const { username, password } = req.body;

    try {
        const account = await userModel.findByUsername(username);

        if (!account) {
            return response(401, null, 'User tidak ditemukan', res);
        }

        const isMatch = await bcrypt.compare(password, account.password);
        if (!isMatch) {
            return response(401, null, 'Password salah', res);
        }

        const token = generateToken({
            id: account.id,
            role: account.role,
            userType: 'user',
            name: account.username
        });

        response(200, { token, role: account.role, userType: 'user' }, 'Login berhasil', res);
    } catch (error) {
        response(500, null, error.message, res);
    }
};

exports.register = async (req, res) => {
    const { username, password, phone, role } = req.body;

    try {
        const hashedPassword = await bcrypt.hash(password, 10);

        const newUser = await userModel.createUser({
            username,
            password: hashedPassword,
            phone,
            role
        });

        response(201, newUser, 'Registrasi berhasil', res);
    } catch (error) {
        response(500, null, error.message, res);
    }
};
