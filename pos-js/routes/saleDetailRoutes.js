const express = require('express');
const router = express.Router();
const saleDetailController = require('../controllers/saleDetailController');
const verifyToken = require('../middleware/authMiddleware');
const authorizeRole = require('../middleware/authorizeRole');

router.get('/', verifyToken, authorizeRole('admin','kasir'), saleDetailController.getAllSaleDetails);
router.get('/:sale_id', verifyToken, authorizeRole('admin', 'kasir'), saleDetailController.getSaleDetailsBySaleId);

module.exports = router;