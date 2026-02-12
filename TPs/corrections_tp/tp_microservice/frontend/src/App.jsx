import React, { useEffect, useState } from "react";
import { getJson, postJson } from "./api/http.js";
import Section from "./components/Section.jsx";

export default function App() {
  const [products, setProducts] = useState([]);
  const [orders, setOrders] = useState([]);
  const [productId, setProductId] = useState("p1");
  const [quantity, setQuantity] = useState(1);
  const [error, setError] = useState("");

  async function refresh() {
    setError("");
    try {
      const [p, o] = await Promise.all([
        getJson("catalog/products"),
        getJson("orders/orders")
      ]);
      setProducts(p);
      setOrders(o);
      if (p.length > 0) setProductId(p[0].id);
    } catch (e) {
      setError(String(e.message || e));
    }
  }

  async function createOrder() {
    setError("");
    try {
      await postJson("orders/orders", { productId, quantity: Number(quantity) });
      await refresh();
    } catch (e) {
      setError(String(e.message || e));
    }
  }

  useEffect(() => {
    refresh();
  }, []);

  return (
    <div style={{ fontFamily: "system-ui", maxWidth: 900, margin: "0 auto", padding: 16 }}>
      <h1 style={{ marginTop: 0 }}>ShopLite</h1>

      {error && (
        <div style={{ background: "#ffe8e8", border: "1px solid #ffb3b3", padding: 12, borderRadius: 8, marginBottom: 16 }}>
          {error}
        </div>
      )}

      <div style={{ display: "grid", gap: 16, gridTemplateColumns: "1fr 1fr" }}>
        <Section title="Catalogue">
          <button onClick={refresh}>Rafraîchir</button>
          <ul>
            {products.map(p => (
              <li key={p.id}>
                <b>{p.name}</b> — {p.price} €
              </li>
            ))}
          </ul>
        </Section>

        <Section title="Créer une commande">
          <div style={{ display: "grid", gap: 8 }}>
            <label>
              Produit
              <select value={productId} onChange={e => setProductId(e.target.value)} style={{ marginLeft: 8 }}>
                {products.map(p => (
                  <option key={p.id} value={p.id}>
                    {p.id} — {p.name}
                  </option>
                ))}
              </select>
            </label>

            <label>
              Quantité
              <input
                type="number"
                min="1"
                value={quantity}
                onChange={e => setQuantity(e.target.value)}
                style={{ marginLeft: 8, width: 80 }}
              />
            </label>

            <button onClick={createOrder}>Commander</button>
          </div>
        </Section>

        <Section title="Commandes" >
          <button onClick={refresh}>Rafraîchir</button>
          <ul>
            {orders.map(o => (
              <li key={o.id}>
                <b>#{o.id}</b> — {o.productId} x{o.quantity} — {o.status}
              </li>
            ))}
          </ul>
        </Section>

        <Section title="Endpoints (via gateway)">
          <ul>
            <li>/catalog/products</li>
            <li>/orders/orders</li>
            <li>/catalog/health</li>
            <li>/orders/health</li>
          </ul>
        </Section>
      </div>
    </div>
  );
}