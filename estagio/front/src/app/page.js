'use client'

import { useEffect, useState } from "react";

const BASE_URL = "http://localhost:8080";

export default function Home() {
  const [user, setUser] = useState({ role: "user" });
  const [produtos, setProdutos] = useState([]);

  useEffect(() => {
    async function consultar() {
      const resposta = await fetch(BASE_URL + "/produtos");
      const aux = await resposta.json();
      setProdutos(aux);
      console.table(produtos);
    }

    consultar();
  }, []);

  return (
    <ul>
      {user.role === 'admin' && <Admin />}
      {user.role !== 'admin' && produtos.map((produto) => {
        return <li>{produto.nome}</li>
      })}
    </ul>
  );
}


function Admin() {
  return <h1>Página do admin</h1>
}