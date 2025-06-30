const db = require('../config/db');

const getAllCategories = () => {
    return new Promise((resolve, reject) => {
        db.query("SELECT * FROM categories", (err, results) => {
            if (err) return reject(err);
            resolve(results);
        });
    });
};

const getCategoryById = (id) => {
    return new Promise((resolve, reject) => {
        db.query("SELECT * FROM categories WHERE id = ?", [id], (err, results) => {
            if (err) return reject(err);
            resolve(results[0]);
        });
    });
};

const createCategory = (data) => {
    return new Promise((resolve, reject) => {
        db.query(
            "INSERT INTO categories (name, description) VALUES (?, ?)",
            [data.name, data.description],
            (err, results) => {
                if (err) return reject(err);
                resolve({ id: results.insertId, ...data });
            }
        );
    });
};

const updateCategory = (id, data) => {
    return new Promise((resolve, reject) => {
        db.query(
            "UPDATE categories SET name = ?, description = ? WHERE id = ?",
            [data.name, data.description, id],
            (err) => {
                if (err) return reject(err);
                resolve({ id, ...data });
            }
        );
    });
};

const deleteCategory = (id) => {
    return new Promise((resolve, reject) => {
        db.query("DELETE FROM categories WHERE id = ?", [id], (err) => {
            if (err) return reject(err);
            resolve({ message: "Category deleted" });
        });
    });
};

module.exports = {
    getAllCategories,
    getCategoryById,
    createCategory,
    updateCategory,
    deleteCategory,
};
