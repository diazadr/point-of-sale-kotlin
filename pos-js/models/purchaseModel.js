// models/purchaseModel.js
const db = require("../config/db");

const getAllPurchases = () => {
  return new Promise((resolve, reject) => {
    const sql = `
      SELECT p.*, s.name AS supplier_name
      FROM purchases p
      LEFT JOIN suppliers s ON p.supplier_id = s.id
    `;
    db.query(sql, (err, results) => {
      if (err) return reject(err);
      resolve(results);
    });
  });
};


const getPurchaseById = (id) => {
  return new Promise((resolve, reject) => {
    const sql = `
      SELECT p.*, s.name AS supplier_name
      FROM purchases p
      LEFT JOIN suppliers s ON p.supplier_id = s.id
      WHERE p.id = ?
    `;
    db.query(sql, [id], (err, results) => {
      if (err) return reject(err);
      resolve(results[0]);
    });
  });
};


const createPurchase = (purchaseData, items) => {
  return new Promise((resolve, reject) => {
    db.beginTransaction((err) => {
      if (err) return reject(err);

      const { date, supplier_id, total_payment } = purchaseData;
      const sqlPurchase = `INSERT INTO purchases (date, supplier_id, total_payment) VALUES (?, ?, ?)`;

      db.query(
        sqlPurchase,
        [date, supplier_id, total_payment],
        (err, result) => {
          if (err) return db.rollback(() => reject(err));

          const purchaseId = result.insertId;

          const detailQueries = items.map((item) => {
            return new Promise((res, rej) => {
              const { product_id, quantity, unit_price, subtotal } = item;
              const sqlDetail = `INSERT INTO purchase_details (purchase_id, product_id, quantity, unit_price, subtotal) VALUES (?, ?, ?, ?, ?)`;
              db.query(
                sqlDetail,
                [purchaseId, product_id, quantity, unit_price, subtotal],
                (err) => {
                  if (err) return rej(err);

                 const sqlUpdateStock = `UPDATE products SET stock_quantity = stock_quantity + ? WHERE id = ?`;
                  db.query(sqlUpdateStock, [quantity, product_id], (err) => {
                    if (err) return rej(err);
                    res();
                  });
                }
              );
            });
          });

          Promise.all(detailQueries)
            .then(() => {
              db.commit((err) => {
                if (err) return db.rollback(() => reject(err));
                resolve({ id: purchaseId, ...purchaseData, items });
              });
            })
            .catch((err) => db.rollback(() => reject(err)));
        }
      );
    });
  });
};

const getPurchasesBySupplierId = async (supplierId) => {
  return new Promise((resolve, reject) => {
    db.query(
      "SELECT * FROM purchases WHERE supplier_id = ?",
      [supplierId],
      (err, results) => {
        if (err) return reject(err);
        resolve(results);
      }
    );
  });
};

module.exports = {
  getAllPurchases,
  getPurchaseById,
  createPurchase,
  getPurchasesBySupplierId,
};
