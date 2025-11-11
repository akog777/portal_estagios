import styles from "./estudante.module.css";

export default function EstudanteDashboard() {
  // Simulação de dados - em produção viria da API
  const vagasRecomendadas = [
    {
      id: 1,
      titulo: "Desenvolvedor Frontend",
      empresa: "Tech Solutions",
      localizacao: "São Paulo, SP",
      modalidade: "Remoto",
      cargaHoraria: 30,
      areas: ["Tecnologia da Informação", "Desenvolvimento Web"],
    },
    {
      id: 2,
      titulo: "Analista de Dados",
      empresa: "Data Corp",
      localizacao: "Rio de Janeiro, RJ",
      modalidade: "Híbrido",
      cargaHoraria: 40,
      areas: ["Ciência de Dados", "Estatística"],
    },
    {
      id: 3,
      titulo: "Designer UX/UI",
      empresa: "Creative Studio",
      localizacao: "Belo Horizonte, MG",
      modalidade: "Presencial",
      cargaHoraria: 30,
      areas: ["Design", "Experiência do Usuário"],
    },
  ];

  return (
    <div className={styles.page}>
      <header className={styles.header}>
        <h1>Portal de Estágios - Estudante</h1>
        <nav className={styles.nav}>
          <a href="/estudante">Minhas Vagas</a>
          <a href="/estudante/perfil">Meu Perfil</a>
          <a href="/estudante/inscricoes">Minhas Inscrições</a>
          <button type="button" className={styles.logout}>
            Sair
          </button>
        </nav>
      </header>

      <main className={styles.main}>
        <div className={styles.welcome}>
          <h2>Olá, João Silva!</h2>
          <p>
            Confira as vagas recomendadas baseadas nas suas áreas de interesse
          </p>
        </div>

        <div className={styles.vagasSection}>
          <h3>Vagas Recomendadas</h3>
          <div className={styles.vagasGrid}>
            {vagasRecomendadas.map((vaga) => (
              <div key={vaga.id} className={styles.vagaCard}>
                <div className={styles.vagaHeader}>
                  <h4>{vaga.titulo}</h4>
                  <span className={styles.empresa}>{vaga.empresa}</span>
                </div>

                <div className={styles.vagaInfo}>
                  <div className={styles.infoItem}>
                    <span className={styles.icon}>📍</span>
                    {vaga.localizacao}
                  </div>
                  <div className={styles.infoItem}>
                    <span className={styles.icon}>💼</span>
                    {vaga.modalidade}
                  </div>
                  <div className={styles.infoItem}>
                    <span className={styles.icon}>⏰</span>
                    {vaga.cargaHoraria}h/semana
                  </div>
                </div>

                <div className={styles.areas}>
                  {vaga.areas.map((area) => (
                    <span key={area} className={styles.areaTag}>
                      {area}
                    </span>
                  ))}
                </div>

                <div className={styles.vagaActions}>
                  <button type="button" className={styles.btnSecondary}>
                    Ver Detalhes
                  </button>
                  <button type="button" className={styles.btnPrimary}>
                    Inscrever-se
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>

        <div className={styles.statsSection}>
          <h3>Seu Progresso</h3>
          <div className={styles.statsGrid}>
            <div className={styles.statItem}>
              <div className={styles.statNumber}>5</div>
              <div className={styles.statLabel}>Inscrições Enviadas</div>
            </div>
            <div className={styles.statItem}>
              <div className={styles.statNumber}>2</div>
              <div className={styles.statLabel}>Entrevistas</div>
            </div>
            <div className={styles.statItem}>
              <div className={styles.statNumber}>0</div>
              <div className={styles.statLabel}>Ofertas Recebidas</div>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}
