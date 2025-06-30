const db = require('../config/db');

const getAllSuppliers = () => {
    return new Promise((resolve, reject) => {
        db.query("SELECT id, name, phone, email, address, supplied_product FROM suppliers", (err, results) => {
            if (err) return reject(err);
            resolve(results);
        });
    });
};

const getSupplierById = (id) => {
    return new Promise((resolve, reject) => {
        db.query("SELECT id, name, phone, email, address, supplied_product FROM suppliers WHERE id = ?", [id], (err, results) => {
            if (err) return reject(err);
            resolve(results[0]);
        });
    });
};

const createSupplier = (data) => {
    return new Promise((resolve, reject) => {
        const sql = `
            INSERT INTO suppliers (name, phone, email, address, supplied_product) 
            VALUES (?, ?, ?, ?, ?)
        `;
        db.query(sql, [
            data.name,
            data.phone,
            data.email,
            data.address,
            data.supplied_product
        ], (err, results) => {
            if (err) return reject(err);
            resolve({ id: results.insertId, ...data });
        });
    });
};

const updateSupplier = (id, data) => {
    return new Promise((resolve, reject) => {
        const sql = `
            UPDATE suppliers 
            SET name = ?, phone = ?, email = ?, address = ?, supplied_product = ?
            WHERE id = ?
        `;
        db.query(sql, [
            data.name,
            data.phone,
            data.email,
            data.address,
            data.supplied_product,
            id
        ], (err) => {
            if (err) return reject(err);
            resolve({ id, ...data });
        });
    });
};

const deleteSupplier = (id) => {
    return new Promise((resolve, reject) => {
        db.query("DELETE FROM suppliers WHERE id = ?", [id], (err) => {
            if (err) return reject(err);
            resolve({ message: "Supplier deleted" });
        });
    });
};

module.exports = {
    getAllSuppliers,
    getSupplierById,
    createSupplier,
    updateSupplier,
    deleteSupplier
};
