const express = require('express');
const router = express.Router();
const purchaseController = require('../controllers/purchaseController');
const verifyToken = require('../middleware/authMiddleware');
const authorizeRole = require('../middleware/authorizeRole');

router.get('/', verifyToken, authorizeRole('admin', 'gudang'), purchaseController.getAllPurchases);
router.get('/:id', verifyToken, authorizeRole('admin', 'gudang'), purchaseController.getPurchaseById);
router.post('/', verifyToken, authorizeRole('admin', 'gudang'), purchaseController.createPurchase);


module.exports = router;
