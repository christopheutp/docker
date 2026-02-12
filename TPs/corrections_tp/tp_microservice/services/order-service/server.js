const express = require("express");
const { Pool } = require("pg");

const app = express();
app.use(express.json());

const pool = new Pool({
  host: process.env.PGHOST,
  user: process.env.PGUSER,
  password: process.env.PGPASSWORD,
  database: process.env.PGDATABASE
});

app.get("/health", async (_, res) => {
  try {
    await pool.query("SELECT 1");
    res.json({ status: "UP" });
  } catch {
    res.status(503).json({ status: "DOWN" });
  }
});

app.post("/orders", async (req, res) => {
  const { productId, quantity } = req.body;
  const r = await pool.query(
    "INSERT INTO orders(product_id, quantity) VALUES($1,$2) RETURNING *",
    [productId, quantity]
  );
  res.status(201).json(r.rows[0]);
});

app.get("/orders", async (_, res) => {
  const r = await pool.query("SELECT * FROM orders ORDER BY id DESC");
  res.json(r.rows);
});

app.listen(3001);