CREATE TABLE expenses (
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR(500) NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    date DATE NOT NULL,
    type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_expenses_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT chk_expenses_amount CHECK (amount > 0),
    CONSTRAINT chk_expenses_type CHECK (type IN ('PERSONAL', 'BUSINESS'))
);

CREATE INDEX idx_expenses_user_id ON expenses(user_id);
CREATE INDEX idx_expenses_type ON expenses(type);
CREATE INDEX idx_expenses_date ON expenses(date);
CREATE INDEX idx_expenses_user_type ON expenses(user_id, type);
CREATE INDEX idx_expenses_user_date ON expenses(user_id, date);

COMMENT ON TABLE expenses IS 'Tabela de despesas dos usuários';
COMMENT ON COLUMN expenses.id IS 'Identificador único da despesa';
COMMENT ON COLUMN expenses.description IS 'Descrição da despesa';
COMMENT ON COLUMN expenses.amount IS 'Valor da despesa';
COMMENT ON COLUMN expenses.date IS 'Data da despesa';
COMMENT ON COLUMN expenses.type IS 'Tipo da despesa (PERSONAL, BUSINESS)';
COMMENT ON COLUMN expenses.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN expenses.updated_at IS 'Data da última atualização do registro';
COMMENT ON COLUMN expenses.user_id IS 'ID do usuário proprietário da despesa';
