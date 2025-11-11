import styles from "./page.module.css";

export default function Home() {
  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <h1>Portal de Estágios</h1>
        <p>Bem-vindo ao sistema de conexão entre empresas e estudantes.</p>
        <div className={styles.ctas}>
          <a className={styles.primary} href="/login">
            Entrar
          </a>
          <a className={styles.secondary} href="/cadastro">
            Cadastrar-se
          </a>
        </div>
      </main>
    </div>
  );
}
