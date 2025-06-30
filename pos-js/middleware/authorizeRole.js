const authorizeRole = (...allowedRoles) => {
    return (req, res, next) => {
        const { role } = req.user;

        if (!allowedRoles.includes(role)) {
            return res.status(403).json({ message: 'Akses ditolak: Role tidak diizinkan' });
        }

        next();
    };
};

module.exports = authorizeRole;
