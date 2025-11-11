import styles from "./login.module.css";

export default function Login() {
  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <h1>Entrar no Portal</h1>
        <form className={styles.form}>
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
          <button type="submit" className={styles.primary}>
            Entrar
          </button>
        </form>
        <p className={styles.link}>
          Não tem conta? <a href="/cadastro">Cadastre-se</a>
        </p>
      </main>
    </div>
  );
}
