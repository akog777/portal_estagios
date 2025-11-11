"use client";

import { useRouter } from "next/navigation";
import { useState } from "react";
import styles from "./login.module.css";

export default function Login() {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [erro, setErro] = useState("");
  const [carregando, setCarregando] = useState(false);
  const router = useRouter();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErro("");
    setCarregando(true);

    try {
      const response = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, senha }),
      });

      if (response.ok) {
        const data = await response.json();

        // Salvar dados do usuário no localStorage
        localStorage.setItem("usuario", JSON.stringify(data));

        // Redirecionar baseado no tipo de usuário
        switch (data.tipo) {
          case "ADMINISTRADOR":
            router.push("/admin");
            break;
          case "EMPRESA":
            router.push("/empresa");
            break;
          case "ESTUDANTE":
            router.push("/estudante");
            break;
          default:
            setErro("Tipo de usuário inválido");
        }
      } else {
        const errorData = await response.json();
        setErro(errorData.message || "Erro ao fazer login");
      }
    } catch (_error) {
      setErro("Erro de conexão. Verifique se o backend está rodando.");
    } finally {
      setCarregando(false);
    }
  };

  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <h1>Entrar no Portal</h1>
        <form className={styles.form} onSubmit={handleSubmit}>
          {erro && <div className={styles.error}>{erro}</div>}

          <div className={styles.inputGroup}>
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              name="email"
              required
              placeholder="seu@email.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="senha">Senha</label>
            <input
              type="password"
              id="senha"
              name="senha"
              required
              placeholder="Sua senha"
              value={senha}
              onChange={(e) => setSenha(e.target.value)}
            />
          </div>
          <button
            type="submit"
            className={styles.primary}
            disabled={carregando}
          >
            {carregando ? "Entrando..." : "Entrar"}
          </button>
        </form>
        <p className={styles.link}>
          Não tem conta? <a href="/cadastro">Cadastre-se</a>
        </p>
      </main>
    </div>
  );
}
