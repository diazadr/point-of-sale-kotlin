const supplierModel = require('../models/supplierModel');
const response = require('../utils/response');

exports.getAllSuppliers = async (req, res) => {
    try {
        const data = await supplierModel.getAllSuppliers();
        response(200, data, "List semua supplier", res);
    } catch (err) {
        response(500, null, err.message, res);
    }
};

exports.getSupplierById = async (req, res) => {
    try {
        const data = await supplierModel.getSupplierById(req.params.id);
        response(200, data, "Detail supplier", res);
    } catch (err) {
        response(500, null, err.message, res);
    }
};

exports.createSupplier = async (req, res) => {
    try {
        const data = await supplierModel.createSupplier(req.body);
        response(201, data, "Supplier berhasil ditambahkan", res);
    } catch (err) {
        response(500, null, err.message, res);
    }
};

exports.updateSupplier = async (req, res) => {
    try {
        const data = await supplierModel.updateSupplier(req.params.id, req.body);
        response(200, data, "Supplier berhasil diupdate", res);
    } catch (err) {
        response(500, null, err.message, res);
    }
};

exports.deleteSupplier = async (req, res) => {
    try {
        const data = await supplierModel.deleteSupplier(req.params.id);
        response(200, data, "Supplier berhasil dihapus", res);
    } catch (err) {
        response(500, null, err.message, res);
    }
};
