const db = require('../config/db');

const getAllProducts = () => {
    return new Promise((resolve, reject) => {
        const sql = `
            SELECT 
                p.*, 
                c.name AS category_name 
            FROM products p 
            LEFT JOIN categories c ON p.category_id = c.id
        `;
        db.query(sql, (err, results) => {
            if (err) return reject(err);
            resolve(results);
        });
    });
};


const getProductById = (id) => {
    return new Promise((resolve, reject) => {
        db.query("SELECT * FROM products WHERE id = ?", [id], (err, results) => {
            if (err) return reject(err);
            resolve(results[0]);
        });
    });
};

const createProduct = (data) => {
    return new Promise((resolve, reject) => {
        const sql = `
            INSERT INTO products 
            (name, item_code, category_id, stock_quantity, purchase_price, selling_price, product_image)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        `;
        db.query(sql, [
            data.name,
            data.item_code,
            data.category_id,
            data.stock_quantity,
            data.purchase_price,
            data.selling_price,
            data.product_image
        ], (err, results) => {
            if (err) return reject(err);
            resolve({ id: results.insertId, ...data });
        });
    });
};

const updateProduct = (id, data) => {
    return new Promise((resolve, reject) => {
        const sql = `
            UPDATE products SET 
            name = ?, item_code = ?, category_id = ?, 
            stock_quantity = ?, purchase_price = ?, selling_price = ?, product_image = ?
            WHERE id = ?
        `;
        db.query(sql, [
            data.name,
            data.item_code,
            data.category_id,
            data.stock_quantity,
            data.purchase_price,
            data.selling_price,
            data.product_image,
            id
        ], (err) => {
            if (err) return reject(err);
            resolve({ id, ...data });
        });
    });
};

const deleteProduct = (id) => {
    return new Promise((resolve, reject) => {
        db.query("DELETE FROM products WHERE id = ?", [id], (err) => {
            if (err) return reject(err);
            resolve({ message: "Product deleted" });
        });
    });
};

const updateProductStock = (id, newStock) => {
    return new Promise((resolve, reject) => {
        db.query("UPDATE products SET stock_quantity = ? WHERE id = ?", [newStock, id], (err) => {
            if (err) return reject(err);
            resolve({ id, stock_quantity: newStock });
        });
    });
};

module.exports = {
    getAllProducts,
    getProductById,
    createProduct,
    updateProduct,
    deleteProduct,
    updateProductStock
};
