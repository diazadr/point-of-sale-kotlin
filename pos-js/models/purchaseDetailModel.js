const db = require('../config/db');

exports.getAllPurchaseDetails = () => {
    return new Promise((resolve, reject) => {
        db.query("SELECT * FROM purchase_details", (err, results) => {
            if (err) return reject(err);
            resolve(results);
        });
    });
};

exports.getPurchaseDetailsByPurchaseId = (purchaseId) => {
  return new Promise((resolve, reject) => {
    const sql = `
      SELECT pd.*, pr.name AS product_name
      FROM purchase_details pd
      LEFT JOIN products pr ON pd.product_id = pr.id
      WHERE pd.purchase_id = ?
    `;
    db.query(sql, [purchaseId], (err, results) => {
      if (err) return reject(err);
      resolve(results);
    });
  });
};
