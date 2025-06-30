const db = require("../config/db");

const getAllSales = () => {
  return new Promise((resolve, reject) => {
    db.query("SELECT * FROM sales", (err, results) => {
      if (err) return reject(err);
      resolve(results);
    });
  });
};

const getSaleById = (id) => {
  return new Promise((resolve, reject) => {
    const sql = `
           SELECT 
    s.id, s.transaction_date, s.total_price, s.payment_method,
    s.customer_id, c.name AS customer_name,
    s.user_id, u.username AS cashier_name,
    sd.product_id, sd.quantity, sd.unit_price, sd.subtotal,
    p.name AS product_name
FROM sales s
JOIN sale_details sd ON s.id = sd.transaction_id
JOIN products p ON sd.product_id = p.id
LEFT JOIN customers c ON s.customer_id = c.id
LEFT JOIN users u ON s.user_id = u.id
WHERE s.id = ?

        `;
    db.query(sql, [id], (err, results) => {
      if (err) return reject(err);

      if (results.length === 0) return resolve(null);

const sale = {
  id: results[0].id,
  transaction_date: results[0].transaction_date,
  total_price: results[0].total_price,
  payment_method: results[0].payment_method,
  customer_id: results[0].customer_id,
  customer_name: results[0].customer_name,
  user_id: results[0].user_id,
user_name: results[0].cashier_name, 
  items: results.map(row => ({
    product_id: row.product_id,
    product_name: row.product_name,
    quantity: row.quantity,
    unit_price: row.unit_price,
    subtotal: row.subtotal
  }))
};

      resolve(sale);
    });
  });
};

const createSale = (data) => {
  return new Promise((resolve, reject) => {
    db.beginTransaction(async (err) => {
      if (err) return reject(err);

      try {
        const saleSql = `
                    INSERT INTO sales (user_id, customer_id, transaction_date, total_price, payment_method)
                    VALUES (?, ?, ?, ?, ?)
                `;
        db.query(
          saleSql,
          [
            data.user_id,
            data.customer_id,
            data.transaction_date,
            data.total_price,
            data.payment_method,
          ],
          async (err, result) => {
            if (err) return db.rollback(() => reject(err));
            const saleId = result.insertId;

            for (const item of data.items) {
              const checkSql = `SELECT stock_quantity FROM products WHERE id = ?`;
              const stockResult = await new Promise((res, rej) => {
                db.query(checkSql, [item.product_id], (err2, rows) => {
                  if (err2) return rej(err2);
                  res(rows[0]);
                });
              });

              if (!stockResult || stockResult.stock_quantity < item.quantity) {
                return db.rollback(() =>
                  reject(
                    new Error(
                      `Stok produk ID ${item.product_id} tidak mencukupi`
                    )
                  )
                );
              }
            }

            const detailSql = `
                            INSERT INTO sale_details (transaction_id, product_id, quantity, unit_price, subtotal)
                            VALUES ?
                        `;
            const values = data.items.map((item) => [
              saleId,
              item.product_id,
              item.quantity,
              item.unit_price,
              item.subtotal,
            ]);

            db.query(detailSql, [values], async (err2) => {
              if (err2) return db.rollback(() => reject(err2));

              try {
                for (const item of data.items) {
                  await new Promise((res, rej) => {
                    db.query(
                      `UPDATE products SET stock_quantity = stock_quantity - ? WHERE id = ?`,
                      [item.quantity, item.product_id],
                      (err3) => {
                        if (err3) return rej(err3);
                        res();
                      }
                    );
                  });
                }

                db.commit((errCommit) => {
                  if (errCommit) return db.rollback(() => reject(errCommit));
                  resolve({
                    id: saleId,
                    user_id: data.user_id,
                    customer_id: data.customer_id,
                    transaction_date: data.transaction_date,
                    total_price: data.total_price,
                    payment_method: data.payment_method,
                    items: data.items,
                  });
                });
              } catch (stockUpdateErr) {
                db.rollback(() => reject(stockUpdateErr));
              }
            });
          }
        );
      } catch (e) {
        db.rollback(() => reject(e));
      }
    });
  });
};

module.exports = {
  getAllSales,
  getSaleById,
  createSale,
};
