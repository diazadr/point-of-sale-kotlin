const categoryModel = require('../models/categoryModel');
const response = require('../utils/response');

exports.getAllCategories = async (req, res) => {
    try {
        const data = await categoryModel.getAllCategories();
        response(200, data, "List categories", res);
    } catch (err) {
        response(500, null, err.message, res);
    }
};

exports.getCategoryById = async (req, res) => {
    try {
        const data = await categoryModel.getCategoryById(req.params.id);
        response(200, data, "Detail category", res);
    } catch (err) {
        response(500, null, err.message, res);
    }
};

exports.createCategory = async (req, res) => {
    try {
        const data = await categoryModel.createCategory(req.body);
        response(201, data, "Category berhasil ditambahkan", res);
    } catch (err) {
        response(500, null, err.message, res);
    }
};

exports.updateCategory = async (req, res) => {
    try {
        const data = await categoryModel.updateCategory(req.params.id, req.body);
        response(200, data, "Category berhasil diupdate", res);
    } catch (err) {
        response(500, null, err.message, res);
    }
};

exports.deleteCategory = async (req, res) => {
    try {
        const data = await categoryModel.deleteCategory(req.params.id);
        response(200, data, "Category berhasil dihapus", res);
    } catch (err) {
        response(500, null, err.message, res);
    }
};
