CREATE TABLE alerts (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    message VARCHAR(1000) NOT NULL,
    type VARCHAR(50) NOT NULL,
    severity VARCHAR(20) NOT NULL,
    read BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_alerts_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT chk_alerts_type CHECK (type IN ('LATE_CHARGE', 'UPCOMING_CHARGE', 'LOW_BALANCE', 'NEGATIVE_BALANCE')),
    CONSTRAINT chk_alerts_severity CHECK (severity IN ('INFO', 'WARNING', 'CRITICAL'))
);

CREATE INDEX idx_alerts_user_id ON alerts(user_id);
CREATE INDEX idx_alerts_read ON alerts(read);
CREATE INDEX idx_alerts_user_read ON alerts(user_id, read);
CREATE INDEX idx_alerts_type ON alerts(type);
CREATE INDEX idx_alerts_severity ON alerts(severity);
CREATE INDEX idx_alerts_created_at ON alerts(created_at);

COMMENT ON TABLE alerts IS 'Tabela de alertas e notificações dos usuários';
COMMENT ON COLUMN alerts.id IS 'Identificador único do alerta';
COMMENT ON COLUMN alerts.title IS 'Título do alerta';
COMMENT ON COLUMN alerts.message IS 'Mensagem detalhada do alerta';
COMMENT ON COLUMN alerts.type IS 'Tipo do alerta (LATE_CHARGE, UPCOMING_CHARGE, LOW_BALANCE, NEGATIVE_BALANCE)';
COMMENT ON COLUMN alerts.severity IS 'Severidade do alerta (INFO, WARNING, CRITICAL)';
COMMENT ON COLUMN alerts.read IS 'Indica se o alerta foi lido';
COMMENT ON COLUMN alerts.created_at IS 'Data de criação do alerta';
COMMENT ON COLUMN alerts.user_id IS 'ID do usuário proprietário do alerta';
