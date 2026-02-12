const express = require('express');
const { Pool } = require('pg');
const redis = require('redis');

const app = express();
app.use(express.json());

const pool = new Pool({
  host: process.env.DB_HOST,
  user: process.env.POSTGRES_USER,
  password: process.env.POSTGRES_PASSWORD,
  database: process.env.POSTGRES_DB,
  port: 5432
});

const redisClient = redis.createClient({
  url: `redis://${process.env.REDIS_HOST}:6379`
});
redisClient.connect();

app.get('/health', (req, res) => {
  res.json({ status: 'healthy', service: 'api' });
});

app.get('/api/stats', async (req, res) => {
  const cached = await redisClient.get('stats');
  if (cached) {
    return res.json({ ...JSON.parse(cached), source: 'cache' });
  }

  const result = await pool.query('SELECT COUNT(*) FROM users');
  const stats = { userCount: result.rows[0].count, timestamp: new Date() };

  await redisClient.setEx('stats', 60, JSON.stringify(stats));
  res.json({ ...stats, source: 'database' });
});

app.get('/api/users', async (req, res) => {
  const result = await pool.query('SELECT * FROM users');
  res.json(result.rows);
});

app.post('/api/users', async (req, res) => {
  const { name, email } = req.body;
  const result = await pool.query(
    'INSERT INTO users (name, email) VALUES ($1, $2) RETURNING *',
    [name, email]
  );
  await redisClient.del('stats');
  res.status(201).json(result.rows[0]);
});

app.listen(3000, () => console.log('API running on port 3000'));
