const express = require('express');
const router = express.Router();
const customerController = require('../controllers/customerController');
const verifyToken = require('../middleware/authMiddleware');
const authorizeRole = require('../middleware/authorizeRole')

router.get('/', verifyToken, authorizeRole('admin', 'kasir'),customerController.getAllCustomers);
router.get('/:id', verifyToken, authorizeRole('admin', 'kasir'),customerController.getCustomerById); 
router.post('/', verifyToken, authorizeRole('admin'), customerController.createCustomer);
router.put('/:id', verifyToken, authorizeRole('admin'),customerController.updateCustomer);
router.delete('/:id', verifyToken, authorizeRole('admin'),customerController.deleteCustomer); 


module.exports = router;
