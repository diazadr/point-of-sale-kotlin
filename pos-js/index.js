require('dotenv').config();
const express = require('express');
const app = express();
const port = process.env.PORT || 3000;

const apiPrefix = '/api';

app.use(`${apiPrefix}/products`, require('./routes/productRoutes'));

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.use(`${apiPrefix}/auth`, require('./routes/authRoutes'));
app.use(`${apiPrefix}/users`, require('./routes/userRoutes'));
app.use(`${apiPrefix}/categories`, require('./routes/categoryRoutes'));
app.use(`${apiPrefix}/suppliers`, require('./routes/supplierRoutes'));
app.use(`${apiPrefix}/customers`, require('./routes/customerRoutes'));
app.use(`${apiPrefix}/sales`, require('./routes/saleRoutes'));
app.use(`${apiPrefix}/sales-detail`, require('./routes/saleDetailRoutes'));
app.use(`${apiPrefix}/purchases`, require('./routes/purchaseRoutes'));
app.use(`${apiPrefix}/purchases-detail`, require('./routes/purchaseDetailRoutes'));
app.use(`${apiPrefix}/sales-reports`, require('./routes/salesReportRoutes'));

app.use('/uploads', express.static('uploads'));

app.get('/', (req, res) => {
    res.send("API POS v1 ready");
});

app.listen(port, '0.0.0.0', () => {
  console.log(`Server berjalan di http://0.0.0.0:${port}`);
});
