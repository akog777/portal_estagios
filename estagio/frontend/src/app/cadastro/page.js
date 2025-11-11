import styles from "./cadastro.module.css";

export default function Cadastro() {
  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <h1>Cadastrar-se</h1>
        <form className={styles.form}>
          <div className={styles.inputGroup}>
            <label htmlFor="tipo">Tipo de Usuário</label>
            <select id="tipo" name="tipo" required>
              <option value="">Selecione...</option>
              <option value="ESTUDANTE">Estudante</option>
              <option value="EMPRESA">Empresa</option>
            </select>
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              name="email"
              required
              placeholder="seu@email.com"
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
            />
          </div>
          <button type="submit" className={styles.primary}>
            Cadastrar
          </button>
        </form>
        <p className={styles.link}>
          Já tem conta? <a href="/login">Entrar</a>
        </p>
      </main>
    </div>
  );
}
