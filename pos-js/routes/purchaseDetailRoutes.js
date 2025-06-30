const express = require('express');
const router = express.Router();
const purchaseDetailController = require('../controllers/purchaseDetailController');
const verifyToken = require('../middleware/authMiddleware');
const authorizeRole = require('../middleware/authorizeRole');

router.get('/', verifyToken, authorizeRole('admin', 'gudang'),purchaseDetailController.getAllPurchaseDetails);
router.get('/by-purchase/:purchase_id', verifyToken, authorizeRole('admin', 'gudang'), purchaseDetailController.getPurchaseDetailsByPurchaseId);


module.exports = router;
