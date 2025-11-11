import styles from "./admin.module.css";

export default function AdminDashboard() {
  return (
    <div className={styles.page}>
      <header className={styles.header}>
        <h1>Portal de Estágios - Administrador</h1>
        <nav className={styles.nav}>
          <a href="/admin">Dashboard</a>
          <a href="/admin/areas">Áreas de Interesse</a>
          <a href="/admin/estatisticas">Estatísticas</a>
          <button type="button" className={styles.logout}>
            Sair
          </button>
        </nav>
      </header>

      <main className={styles.main}>
        <h2>Dashboard Administrativo</h2>

        <div className={styles.statsGrid}>
          <div className={styles.statCard}>
            <h3>Empresas Cadastradas</h3>
            <div className={styles.statNumber}>0</div>
          </div>

          <div className={styles.statCard}>
            <h3>Estudantes Cadastrados</h3>
            <div className={styles.statNumber}>0</div>
          </div>

          <div className={styles.statCard}>
            <h3>Vagas Abertas</h3>
            <div className={styles.statNumber}>0</div>
          </div>

          <div className={styles.statCard}>
            <h3>Vagas Encerradas</h3>
            <div className={styles.statNumber}>0</div>
          </div>
        </div>

        <div className={styles.chartSection}>
          <h3>Vagas por Área de Interesse</h3>
          <div className={styles.chartPlaceholder}>
            <p>Gráfico será implementado aqui</p>
          </div>
        </div>

        <div className={styles.recentActivity}>
          <h3>Atividades Recentes</h3>
          <ul className={styles.activityList}>
            <li>Nova empresa cadastrada: Tech Solutions</li>
            <li>Nova área de interesse adicionada: Inteligência Artificial</li>
            <li>Vaga encerrada: Desenvolvedor Frontend</li>
          </ul>
        </div>
      </main>
    </div>
  );
}
