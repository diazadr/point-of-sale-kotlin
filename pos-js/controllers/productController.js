const fs = require('fs');
const path = require('path');
const productModel = require('../models/productModel');
const response = require('../utils/response');

exports.getAllProducts = async (req, res) => {
    try {
        const products = await productModel.getAllProducts();
        const baseUrl = `${req.protocol}://${req.get('host')}`;

        const updatedProducts = products.map(product => ({
            ...product,
            product_image_url: product.product_image 
                ? `${baseUrl}/uploads/${product.product_image}` 
                : null
        }));

        response(200, updatedProducts, "Daftar produk", res);
    } catch (error) {
        response(500, null, error.message, res);
    }
};

exports.getProductById = async (req, res) => {
    try {
        const product = await productModel.getProductById(req.params.id);
        if (!product) return response(404, null, "Produk tidak ditemukan", res);

        const baseUrl = `${req.protocol}://${req.get('host')}`;
        product.product_image_url = product.product_image 
            ? `${baseUrl}/uploads/${product.product_image}` 
            : null;

        response(200, product, "Detail produk", res);
    } catch (error) {
        response(500, null, error.message, res);
    }
};

exports.createProduct = async (req, res) => {
    try {
        if (!req.file) {
            return response(400, null, "File gambar produk diperlukan", res);
        }

        const data = {
            ...req.body,
            product_image: req.file.filename
        };

        const product = await productModel.createProduct(data);
        response(201, product, "Produk berhasil ditambahkan", res);
    } catch (error) {
        response(500, null, error.message, res);
    }
};

exports.updateProduct = async (req, res) => {
    try {
        const product = await productModel.getProductById(req.params.id);
        if (!product) return response(404, null, "Produk tidak ditemukan", res);

        let newImage = product.product_image;

        if (req.file) {
            if (product.product_image) {
                const oldImagePath = path.join(__dirname, '..', 'uploads', product.product_image);
                if (fs.existsSync(oldImagePath)) {
                    fs.unlinkSync(oldImagePath);
                }
            }
            newImage = req.file.filename;
        }

        const updatedData = {
            ...req.body,
            product_image: newImage
        };

        const updatedProduct = await productModel.updateProduct(req.params.id, updatedData);
        response(200, updatedProduct, "Produk berhasil diperbarui", res);
    } catch (error) {
        response(500, null, error.message, res);
    }
};

exports.deleteProduct = async (req, res) => {
    try {
        const product = await productModel.getProductById(req.params.id);
        if (!product) return response(404, null, "Produk tidak ditemukan", res);

        if (product.product_image) {
            const imagePath = path.join(__dirname, '..', 'uploads', product.product_image);
            if (fs.existsSync(imagePath)) {
                fs.unlinkSync(imagePath);
            }
        }

        await productModel.deleteProduct(req.params.id);
        response(200, null, "Produk berhasil dihapus", res);
    } catch (error) {
        response(500, null, error.message, res);
    }
};
