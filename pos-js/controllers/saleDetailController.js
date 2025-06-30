const saleDetailModel = require('../models/saleDetailModel');
const response = require('../utils/response');

exports.getAllSaleDetails = async (req, res) => {
    try {
        const data = await saleDetailModel.getAllSaleDetails();
        response(200, data, "Semua detail penjualan", res);
    } catch (err) {
        response(500, null, err.message, res);
    }
};

exports.getSaleDetailsBySaleId = async (req, res) => {
    try {
       const data = await saleDetailModel.getSaleDetailsBySaleId(req.params.sale_id);

        response(200, data, `Detail penjualan untuk sale_id ${req.params.sale_id}`, res);
    } catch (err) {
        response(500, null, err.message, res);
    }
};