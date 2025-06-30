const response = (statusCode, data, message, res) => {
    res.status(statusCode).json({
        status: statusCode,
        message: message,
        payload: data,
        metadata: {
            prev: "",
            next: "",
            current: ""
        }
    });
};

module.exports = response;
