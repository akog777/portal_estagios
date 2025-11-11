import styles from "./empresa.module.css";

export default function EmpresaDashboard() {
  // Simulação de dados - em produção viria da API
  const vagasAtivas = [
    {
      id: 1,
      titulo: "Desenvolvedor Frontend",
      inscricoes: 12,
      status: "Ativa",
      dataCriacao: "2024-01-15",
    },
    {
      id: 2,
      titulo: "Analista de Dados",
      inscricoes: 8,
      status: "Ativa",
      dataCriacao: "2024-01-20",
    },
  ];

  const candidatosRecentes = [
    {
      id: 1,
      nome: "João Silva",
      curso: "Ciência da Computação",
      vaga: "Desenvolvedor Frontend",
      dataInscricao: "2024-01-25",
    },
    {
      id: 2,
      nome: "Maria Santos",
      curso: "Engenharia de Software",
      vaga: "Analista de Dados",
      dataInscricao: "2024-01-24",
    },
    {
      id: 3,
      nome: "Pedro Oliveira",
      curso: "Sistemas de Informação",
      vaga: "Desenvolvedor Frontend",
      dataInscricao: "2024-01-23",
    },
  ];

  return (
    <div className={styles.page}>
      <header className={styles.header}>
        <h1>Portal de Estágios - Empresa</h1>
        <nav className={styles.nav}>
          <a href="/empresa">Dashboard</a>
          <a href="/empresa/vagas">Minhas Vagas</a>
          <a href="/empresa/candidatos">Candidatos</a>
          <button type="button" className={styles.btnPrimary}>
            Criar Vaga
          </button>
          <button type="button" className={styles.logout}>
            Sair
          </button>
        </nav>
      </header>

      <main className={styles.main}>
        <div className={styles.welcome}>
          <h2>Olá, Tech Solutions!</h2>
          <p>Gerencie suas vagas de estágio e acompanhe os candidatos</p>
        </div>

        <div className={styles.statsGrid}>
          <div className={styles.statCard}>
            <h3>Vagas Ativas</h3>
            <div className={styles.statNumber}>{vagasAtivas.length}</div>
          </div>
          <div className={styles.statCard}>
            <h3>Total de Inscrições</h3>
            <div className={styles.statNumber}>
              {vagasAtivas.reduce((total, vaga) => total + vaga.inscricoes, 0)}
            </div>
          </div>
          <div className={styles.statCard}>
            <h3>Candidatos Novos</h3>
            <div className={styles.statNumber}>5</div>
          </div>
          <div className={styles.statCard}>
            <h3>Vagas Encerradas</h3>
            <div className={styles.statNumber}>3</div>
          </div>
        </div>

        <div className={styles.contentGrid}>
          <div className={styles.section}>
            <div className={styles.sectionHeader}>
              <h3>Minhas Vagas Ativas</h3>
              <a href="/empresa/vagas" className={styles.link}>
                Ver todas
              </a>
            </div>
            <div className={styles.vagasList}>
              {vagasAtivas.map((vaga) => (
                <div key={vaga.id} className={styles.vagaItem}>
                  <div className={styles.vagaInfo}>
                    <h4>{vaga.titulo}</h4>
                    <div className={styles.vagaMeta}>
                      <span>{vaga.inscricoes} inscrições</span>
                      <span className={styles.status}>{vaga.status}</span>
                    </div>
                  </div>
                  <div className={styles.vagaActions}>
                    <button type="button" className={styles.btnSecondary}>
                      Editar
                    </button>
                    <button type="button" className={styles.btnDanger}>
                      Encerrar
                    </button>
                  </div>
                </div>
              ))}
            </div>
          </div>

          <div className={styles.section}>
            <div className={styles.sectionHeader}>
              <h3>Candidatos Recentes</h3>
              <a href="/empresa/candidatos" className={styles.link}>
                Ver todos
              </a>
            </div>
            <div className={styles.candidatosList}>
              {candidatosRecentes.map((candidato) => (
                <div key={candidato.id} className={styles.candidatoItem}>
                  <div className={styles.candidatoInfo}>
                    <h4>{candidato.nome}</h4>
                    <p>{candidato.curso}</p>
                    <small>Vaga: {candidato.vaga}</small>
                  </div>
                  <div className={styles.candidatoActions}>
                    <button type="button" className={styles.btnSecondary}>
                      Ver Perfil
                    </button>
                    <button type="button" className={styles.btnPrimary}>
                      Contatar
                    </button>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}
