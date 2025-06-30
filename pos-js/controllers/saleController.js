const saleModel = require('../models/saleModel');
const response = require('../utils/response');

exports.getAllSales = async (req, res) => {
    try {
        const sales = await saleModel.getAllSales();
        response(200, sales, "List semua sales", res);
    } catch (error) {
        response(500, null, error.message, res);
    }
};

exports.getSaleById = async (req, res) => {
    try {
        const sale = await saleModel.getSaleById(req.params.id);
        if (!sale) {
            return response(404, null, "Transaksi tidak ditemukan", res);
        }
        response(200, sale, "Detail transaksi", res);
    } catch (error) {
        response(500, null, error.message, res);
    }
};

exports.createSale = async (req, res) => {
    try {
        const saleData = {
            ...req.body,
            user_id: req.user.id
        };

        const sale = await saleModel.createSale(saleData);
        response(201, sale, "Transaksi berhasil disimpan", res);
    } catch (error) {
        response(500, null, error.message, res);
    }
};
