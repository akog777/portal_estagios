"use client";

import { useRouter } from "next/navigation";
import { useState } from "react";
import styles from "./cadastro.module.css";

export default function Cadastro() {
  const [tipoUsuario, setTipoUsuario] = useState("");
  const [formData, setFormData] = useState({});
  const [erro, setErro] = useState("");
  const [carregando, setCarregando] = useState(false);
  const router = useRouter();

  const handleTipoChange = (e) => {
    setTipoUsuario(e.target.value);
    setFormData({});
    setErro("");
  };

  const handleInputChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErro("");
    setCarregando(true);

    try {
      let endpoint = "";
      let payload = {};

      switch (tipoUsuario) {
        case "ESTUDANTE":
          endpoint = "http://localhost:8080/api/estudantes";
          payload = {
            nome: formData.nome,
            cpf: formData.cpf,
            curso: formData.curso,
            telefone: formData.telefone,
            usuario: {
              email: formData.email,
              senha: formData.senha,
            },
          };
          break;

        case "EMPRESA":
          endpoint = "http://localhost:8080/api/empresas";
          payload = {
            nome: formData.nome,
            cnpj: formData.cnpj,
            telefone: formData.telefone,
            endereco: formData.endereco,
            usuario: {
              email: formData.email,
              senha: formData.senha,
            },
          };
          break;

        case "ADMINISTRADOR":
          endpoint = "http://localhost:8080/api/administradores";
          payload = {
            nome: formData.nome,
            usuario: {
              email: formData.email,
              senha: formData.senha,
            },
          };
          break;

        default:
          setErro("Tipo de usuário inválido");
          setCarregando(false);
          return;
      }

      const response = await fetch(endpoint, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      });

      if (response.ok) {
        alert("Cadastro realizado com sucesso! Faça o login.");
        router.push("/login");
      } else {
        const errorData = await response.json();
        setErro(errorData.message || "Erro ao cadastrar");
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
        <h1>Cadastrar-se</h1>
        <form className={styles.form} onSubmit={handleSubmit}>
          {erro && <div className={styles.error}>{erro}</div>}

          <div className={styles.inputGroup}>
            <label htmlFor="tipo">Tipo de Usuário</label>
            <select
              id="tipo"
              name="tipo"
              required
              onChange={handleTipoChange}
              value={tipoUsuario}
            >
              <option value="">Selecione...</option>
              <option value="ESTUDANTE">Estudante</option>
              <option value="EMPRESA">Empresa</option>
              <option value="ADMINISTRADOR">Administrador</option>
            </select>
          </div>

          {tipoUsuario === "ESTUDANTE" && (
            <FormularioEstudante
              formData={formData}
              onChange={handleInputChange}
            />
          )}
          {tipoUsuario === "EMPRESA" && (
            <FormularioEmpresa
              formData={formData}
              onChange={handleInputChange}
            />
          )}
          {tipoUsuario === "ADMINISTRADOR" && (
            <FormularioAdministrador
              formData={formData}
              onChange={handleInputChange}
            />
          )}

          {tipoUsuario && (
            <button
              type="submit"
              className={styles.primary}
              disabled={carregando}
            >
              {carregando ? "Cadastrando..." : "Cadastrar"}
            </button>
          )}
        </form>
        <p className={styles.link}>
          Já tem conta? <a href="/login">Entrar</a>
        </p>
      </main>
    </div>
  );
}

function FormularioEstudante({ formData, onChange }) {
  return (
    <>
      <div className={styles.inputGroup}>
        <label htmlFor="nome">Nome Completo</label>
        <input
          type="text"
          id="nome"
          name="nome"
          required
          placeholder="Seu nome completo"
          value={formData.nome || ""}
          onChange={onChange}
        />
      </div>
      <div className={styles.inputGroup}>
        <label htmlFor="cpf">CPF</label>
        <input
          type="text"
          id="cpf"
          name="cpf"
          required
          placeholder="000.000.000-00"
          value={formData.cpf || ""}
          onChange={onChange}
        />
      </div>
      <div className={styles.inputGroup}>
        <label htmlFor="curso">Curso</label>
        <input
          type="text"
          id="curso"
          name="curso"
          required
          placeholder="Nome do curso"
          value={formData.curso || ""}
          onChange={onChange}
        />
      </div>
      <div className={styles.inputGroup}>
        <label htmlFor="telefone">Telefone</label>
        <input
          type="tel"
          id="telefone"
          name="telefone"
          required
          placeholder="(00) 00000-0000"
          value={formData.telefone || ""}
          onChange={onChange}
        />
      </div>
      <div className={styles.inputGroup}>
        <label htmlFor="email">Email</label>
        <input
          type="email"
          id="email"
          name="email"
          required
          placeholder="seu@email.com"
          value={formData.email || ""}
          onChange={onChange}
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
          value={formData.senha || ""}
          onChange={onChange}
        />
      </div>
      <div className={styles.inputGroup}>
        <label htmlFor="confirmarSenha">Confirmar Senha</label>
        <input
          type="password"
          id="confirmarSenha"
          name="confirmarSenha"
          required
          placeholder="Confirme sua senha"
          value={formData.confirmarSenha || ""}
          onChange={onChange}
        />
      </div>
    </>
  );
}

function FormularioEmpresa({ formData, onChange }) {
  return (
    <>
      <div className={styles.inputGroup}>
        <label htmlFor="nome">Nome da Empresa</label>
        <input
          type="text"
          id="nome"
          name="nome"
          required
          placeholder="Nome da empresa"
          value={formData.nome || ""}
          onChange={onChange}
        />
      </div>
      <div className={styles.inputGroup}>
        <label htmlFor="cnpj">CNPJ</label>
        <input
          type="text"
          id="cnpj"
          name="cnpj"
          required
          placeholder="00.000.000/0000-00"
          value={formData.cnpj || ""}
          onChange={onChange}
        />
      </div>
      <div className={styles.inputGroup}>
        <label htmlFor="telefone">Telefone</label>
        <input
          type="tel"
          id="telefone"
          name="telefone"
          required
          placeholder="(00) 00000-0000"
          value={formData.telefone || ""}
          onChange={onChange}
        />
      </div>
      <div className={styles.inputGroup}>
        <label htmlFor="email">Email</label>
        <input
          type="email"
          id="email"
          name="email"
          required
          placeholder="empresa@email.com"
          value={formData.email || ""}
          onChange={onChange}
        />
      </div>
      <div className={styles.inputGroup}>
        <label htmlFor="endereco">Endereço</label>
        <input
          type="text"
          id="endereco"
          name="endereco"
          required
          placeholder="Endereço completo"
          value={formData.endereco || ""}
          onChange={onChange}
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
          value={formData.senha || ""}
          onChange={onChange}
        />
      </div>
      <div className={styles.inputGroup}>
        <label htmlFor="confirmarSenha">Confirmar Senha</label>
        <input
          type="password"
          id="confirmarSenha"
          name="confirmarSenha"
          required
          placeholder="Confirme sua senha"
          value={formData.confirmarSenha || ""}
          onChange={onChange}
        />
      </div>
    </>
  );
}

function FormularioAdministrador({ formData, onChange }) {
  return (
    <>
      <div className={styles.inputGroup}>
        <label htmlFor="nome">Nome Completo</label>
        <input
          type="text"
          id="nome"
          name="nome"
          required
          placeholder="Seu nome completo"
          value={formData.nome || ""}
          onChange={onChange}
        />
      </div>
      <div className={styles.inputGroup}>
        <label htmlFor="email">Email</label>
        <input
          type="email"
          id="email"
          name="email"
          required
          placeholder="admin@email.com"
          value={formData.email || ""}
          onChange={onChange}
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
          value={formData.senha || ""}
          onChange={onChange}
        />
      </div>
      <div className={styles.inputGroup}>
        <label htmlFor="confirmarSenha">Confirmar Senha</label>
        <input
          type="password"
          id="confirmarSenha"
          name="confirmarSenha"
          required
          placeholder="Confirme sua senha"
          value={formData.confirmarSenha || ""}
          onChange={onChange}
        />
      </div>
    </>
  );
}
