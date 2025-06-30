const userModel = require('../models/userModel');
const response = require('../utils/response');

exports.getAllUsers = async (req, res) => {
    try {
        const users = await userModel.getAllUsers();
        response(200, users, "List semua user", res);
    } catch (error) {
        response(500, null, error.message, res);
    }
};

exports.getUserById = async (req, res) => {
    try {
        const user = await userModel.getUserById(req.params.id);
        if (!user) return response(404, null, "User tidak ditemukan", res);
        response(200, user, "Detail user", res);
    } catch (error) {
        response(500, null, error.message, res);
    }
};

exports.createUser = async (req, res) => {
    try {
        const user = await userModel.createUser(req.body);
        response(201, user, "User berhasil ditambahkan", res);
    } catch (error) {
        response(500, null, error.message, res);
    }
};

exports.updateUser = async (req, res) => {
    try {
        const user = await userModel.updateUser(req.params.id, req.body);
        response(200, user, "User berhasil diupdate", res);
    } catch (error) {
        response(500, null, error.message, res);
    }
};

exports.deleteUser = async (req, res) => {
    try {
        const result = await userModel.deleteUser(req.params.id);
        response(200, result, "User berhasil dihapus", res);
    } catch (error) {
        response(500, null, error.message, res);
    }
};
