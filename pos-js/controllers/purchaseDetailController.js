const purchaseDetailModel = require('../models/purchaseDetailModel');
const response = require('../utils/response');

exports.getAllPurchaseDetails = async (req, res) => {
    try {
        const data = await purchaseDetailModel.getAllPurchaseDetails();
        response(200, data, "Semua detail pembelian", res);
    } catch (err) {
        response(500, null, err.message, res);
    }
};

exports.getPurchaseDetailsByPurchaseId = async (req, res) => {
    try {
        const data = await purchaseDetailModel.getPurchaseDetailsByPurchaseId(req.params.purchase_id);
        response(200, data, `Detail pembelian untuk purchase_id ${req.params.purchase_id}`, res);
    } catch (err) {
        response(500, null, err.message, res);
    }
};
