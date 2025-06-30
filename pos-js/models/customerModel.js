const db = require('../config/db');

const getAllCustomers = () => {
    return new Promise((resolve, reject) => {
        db.query("SELECT id, name, phone, email, address, customer_type FROM customers", (err, results) => {
            if (err) return reject(err);
            resolve(results);
        });
    });
};

const getCustomerById = (id) => {
    return new Promise((resolve, reject) => {
        db.query("SELECT id, name, phone, email, address, customer_type FROM customers WHERE id = ?", [id], (err, results) => {
            if (err) return reject(err);
            resolve(results[0]);
        });
    });
};

const createCustomer = (data) => {
    return new Promise((resolve, reject) => {
        const sql = `
            INSERT INTO customers (name, phone, email, address, customer_type)
            VALUES (?, ?, ?, ?, ?)
        `;
        db.query(sql, [
            data.name,
            data.phone,
            data.email,
            data.address,
            data.customer_type
        ], (err, results) => {
            if (err) return reject(err);
            resolve({ id: results.insertId, ...data });
        });
    });
};

const updateCustomer = (id, data) => {
    return new Promise((resolve, reject) => {
        const sql = `
            UPDATE customers 
            SET name = ?, phone = ?, email = ?, address = ?, customer_type = ?
            WHERE id = ?
        `;
        db.query(sql, [
            data.name,
            data.phone,
            data.email,
            data.address,
            data.customer_type,
            id
        ], (err) => {
            if (err) return reject(err);
            resolve({ id, ...data });
        });
    });
};

const deleteCustomer = (id) => {
    return new Promise((resolve, reject) => {
        db.query("DELETE FROM customers WHERE id = ?", [id], (err) => {
            if (err) return reject(err);
            resolve({ message: "Customer deleted" });
        });
    });
};

module.exports = {
    getAllCustomers,
    getCustomerById,
    createCustomer,
    updateCustomer,
    deleteCustomer
};
