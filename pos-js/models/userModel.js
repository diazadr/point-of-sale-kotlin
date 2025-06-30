const db = require('../config/db');

const getAllUsers = () => {
    return new Promise((resolve, reject) => {
        db.query("SELECT * FROM users", (err, results) => {
            if (err) return reject(err);
            resolve(results);
        });
    });
};

const getUserById = (id) => {
    return new Promise((resolve, reject) => {
        db.query("SELECT * FROM users WHERE id = ?", [id], (err, results) => {
            if (err) return reject(err);
            resolve(results[0]);
        });
    });
};

const createUser = (data) => {
    return new Promise((resolve, reject) => {
        const sql = `INSERT INTO users (username, password, phone, role) VALUES (?, ?, ?, ?)`;
        db.query(sql, [data.username, data.password, data.phone, data.role], (err, results) => {
            if (err) return reject(err);
            resolve({ id: results.insertId, ...data });
        });
    });
};


const updateUser = (id, data) => {
    return new Promise((resolve, reject) => {
        const sql = `UPDATE users SET username = ?, password = ?, phone = ?, role = ? WHERE id = ?`;
        db.query(sql, [data.username, data.password, data.phone, data.role, id], (err, results) => {
            if (err) return reject(err);
            resolve({ id, ...data });
        });
    });
};

const deleteUser = (id) => {
    return new Promise((resolve, reject) => {
        db.query("DELETE FROM users WHERE id = ?", [id], (err, results) => {
            if (err) return reject(err);
            resolve({ message: "User berhasil dihapus" });
        });
    });
};

const findByUsername = (username) => {
    return new Promise((resolve, reject) => {
        db.query("SELECT * FROM users WHERE username = ?", [username], (err, results) => {
            if (err) return reject(err);
            resolve(results[0]);
        });
    });
};


module.exports = {
    getAllUsers,
    getUserById,
    createUser,
    updateUser,
    deleteUser,
    findByUsername
};
