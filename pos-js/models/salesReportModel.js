const db = require('../config/db');

const getSalesByProduct = () => {
    return new Promise((resolve, reject) => {
        const sql = `
            SELECT p.name AS product_name, SUM(sd.quantity) AS total_quantity, SUM(sd.subtotal) AS total_sales
            FROM sale_details sd
            JOIN products p ON sd.product_id = p.id
            GROUP BY sd.product_id
            ORDER BY total_sales DESC
        `;
        db.query(sql, (err, results) => {
            if (err) return reject(err);
            resolve(results);
        });
    });
};

const getSalesSummaryPerPeriod = () => {
    return new Promise((resolve, reject) => {
        const sql = `
            SELECT 
                report_period,
                total_revenue,
                total_transactions,
                p.name AS best_selling_product
            FROM sales_reports sr
            LEFT JOIN products p ON sr.best_selling_product_id = p.id
            ORDER BY report_period DESC
        `;
        db.query(sql, (err, results) => {
            if (err) return reject(err);
            resolve(results);
        });
    });
};

module.exports = {
    getSalesByProduct,
    getSalesSummaryPerPeriod
};
