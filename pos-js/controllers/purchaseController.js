const purchaseModel = require('../models/purchaseModel');
const response = require('../utils/response');

exports.getAllPurchases = async (req, res) => {
    try {
        let data;
        if (req.user.userType === 'supplier') {
            const supplierId = req.user.id;
            data = await purchaseModel.getPurchasesBySupplierId(supplierId);
        } else {
            data = await purchaseModel.getAllPurchases();
        }

        data = await purchaseModel.getAllPurchases();
        response(200, data, "List pembelian", res);
    } catch (err) {
        response(500, null, err.message, res);
    }
};

exports.getPurchaseById = async (req, res) => {
    try {
        const data = await purchaseModel.getPurchaseById(req.params.id);
        response(200, data, "Detail pembelian", res);
    } catch (error) {
        response(500, null, error.message, res);
    }
};

exports.createPurchase = async (req, res) => {
    try {
        const { date, supplier_id, total_payment, items } = req.body;
        const data = await purchaseModel.createPurchase({ date, supplier_id, total_payment }, items);
        response(201, data, "Pembelian berhasil ditambahkan", res);
    } catch (error) {
        response(500, null, error.message, res);
    }
};