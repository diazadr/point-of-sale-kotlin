const model = require('../models/salesReportModel');
const response = require('../utils/response');

exports.getSalesByProduct = async (req, res) => {
    try {
        const data = await model.getSalesByProduct();
        response(200, data, "Laporan penjualan per produk", res);
    } catch (error) {
        response(500, null, error.message, res);
    }
};

exports.getSalesSummaryPerPeriod = async (req, res) => {
    try {
        const data = await model.getSalesSummaryPerPeriod();
        response(200, data, "Laporan ringkasan penjualan per periode", res);
    } catch (error) {
        response(500, null, error.message, res);
    }
};
