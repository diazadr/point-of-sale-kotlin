const express = require('express');
const router = express.Router();
const userController = require('../controllers/userController');
const verifyToken = require('../middleware/authMiddleware');
const authorizeRole = require('../middleware/authorizeRole')

router.get('/', verifyToken, authorizeRole('admin'), userController.getAllUsers);
router.get('/:id', verifyToken, authorizeRole('admin'), userController.getUserById);
router.post('/', verifyToken, authorizeRole('admin'), userController.createUser);
router.put('/:id', verifyToken, authorizeRole('admin'), userController.updateUser);
router.delete('/:id', verifyToken, authorizeRole('admin'), userController.deleteUser);

module.exports = router;
