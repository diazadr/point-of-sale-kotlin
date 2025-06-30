const customerModel = require('../models/customerModel');
const response = require('../utils/response');

exports.getAllCustomers = async (req, res) => {
    try {
        const customers = await customerModel.getAllCustomers();
        response(200, customers, "List semua pelanggan", res);
    } catch (err) {
        response(500, null, err.message, res);
    }
};

exports.getCustomerById = async (req, res) => {
    try {
        const customer = await customerModel.getCustomerById(req.params.id);
        response(200, customer, "Detail pelanggan", res);
    } catch (err) {
        response(500, null, err.message, res);
    }
};

exports.createCustomer = async (req, res) => {
    try {
        const customer = await customerModel.createCustomer(req.body);
        response(201, customer, "Pelanggan berhasil ditambahkan", res);
    } catch (err) {
        response(500, null, err.message, res);
    }
};

exports.updateCustomer = async (req, res) => {
    try {
        const customer = await customerModel.updateCustomer(req.params.id, req.body);
        response(200, customer, "Pelanggan berhasil diperbarui", res);
    } catch (err) {
        response(500, null, err.message, res);
    }
};

exports.deleteCustomer = async (req, res) => {
    try {
        const result = await customerModel.deleteCustomer(req.params.id);
        response(200, result, "Pelanggan berhasil dihapus", res);
    } catch (err) {
        response(500, null, err.message, res);
    }
};
