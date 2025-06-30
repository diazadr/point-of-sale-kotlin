const db = require("../config/db");

exports.getAllSaleDetails = () => {
  return new Promise((resolve, reject) => {
    db.query("SELECT * FROM sale_details", (err, results) => {
      if (err) return reject(err);
      resolve(results);
    });
  });
};

exports.getSaleDetailsBySaleId = (saleId) => {
  return new Promise((resolve, reject) => {
    db.query(
      "SELECT * FROM sale_details WHERE transaction_id = ?",
      [saleId],
      (err, results) => {
        if (err) return reject(err);
        resolve(results);
      }
    );
  });
};
