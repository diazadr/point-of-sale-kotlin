const express = require('express');
const router = express.Router();
const saleController = require('../controllers/saleController');
const verifyToken = require('../middleware/authMiddleware');
const authorizeRole = require('../middleware/authorizeRole');

router.get('/',verifyToken, authorizeRole('admin', 'kasir'), saleController.getAllSales);
router.get('/:id', verifyToken, authorizeRole('admin', 'kasir'),saleController.getSaleById);
router.post('/',verifyToken, authorizeRole('admin', 'kasir'), saleController.createSale);

module.exports = router;
