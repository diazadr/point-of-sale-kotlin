const express = require('express');
const router = express.Router();
const controller = require('../controllers/salesReportController');
const verifyToken = require('../middleware/authMiddleware');
const authorizeRole = require('../middleware/authorizeRole');

router.get('/by-product', verifyToken, authorizeRole('admin'), controller.getSalesByProduct);
router.get('/summary', verifyToken, authorizeRole('admin'), controller.getSalesSummaryPerPeriod);

module.exports = router;
