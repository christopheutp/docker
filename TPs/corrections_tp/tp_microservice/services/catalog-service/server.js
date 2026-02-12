const express = require("express");
const app = express();

app.get("/health", (_, res) => res.json({ status: "UP" }));

app.get("/products", (_, res) => {
  res.json([
    { id: "p1", name: "Clavier", price: 49.9 },
    { id: "p2", name: "Souris", price: 19.9 },
    { id: "p3", name: "Écran", price: 179.9 }
  ]);
});

app.listen(3000);