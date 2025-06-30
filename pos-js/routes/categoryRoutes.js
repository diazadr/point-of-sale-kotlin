const express = require('express');
const router = express.Router();
const categoryController = require('../controllers/categoryController');
const verifyToken = require('../middleware/authMiddleware');
const authorizeRole = require('../middleware/authorizeRole');

router.get('/', verifyToken, authorizeRole('admin'), categoryController.getAllCategories);
router.get('/:id', verifyToken, authorizeRole('admin'), categoryController.getCategoryById);
router.post('/',  verifyToken, authorizeRole('admin'), categoryController.createCategory);
router.put('/:id', verifyToken, authorizeRole('admin'), categoryController.updateCategory);
router.delete('/:id', verifyToken, authorizeRole('admin'), categoryController.deleteCategory);

module.exports = router;
