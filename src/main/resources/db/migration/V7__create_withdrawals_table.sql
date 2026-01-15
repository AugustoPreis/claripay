CREATE TABLE withdrawals (
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR(500) NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    date DATE NOT NULL,
    type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_withdrawals_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT chk_withdrawals_amount CHECK (amount > 0),
    CONSTRAINT chk_withdrawals_type CHECK (type IN ('PRO_LABORE', 'DIVIDEND', 'PERSONAL_USE', 'REINVESTMENT'))
);

CREATE INDEX idx_withdrawals_user_id ON withdrawals(user_id);
CREATE INDEX idx_withdrawals_type ON withdrawals(type);
CREATE INDEX idx_withdrawals_date ON withdrawals(date);
CREATE INDEX idx_withdrawals_user_type ON withdrawals(user_id, type);
CREATE INDEX idx_withdrawals_user_date ON withdrawals(user_id, date);

COMMENT ON TABLE withdrawals IS 'Tabela de retiradas do negócio para uso pessoal';
COMMENT ON COLUMN withdrawals.id IS 'Identificador único da retirada';
COMMENT ON COLUMN withdrawals.description IS 'Descrição da retirada';
COMMENT ON COLUMN withdrawals.amount IS 'Valor da retirada';
COMMENT ON COLUMN withdrawals.date IS 'Data da retirada';
COMMENT ON COLUMN withdrawals.type IS 'Tipo da retirada (PRO_LABORE, DIVIDEND, PERSONAL_USE, REINVESTMENT)';
COMMENT ON COLUMN withdrawals.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN withdrawals.updated_at IS 'Data da última atualização do registro';
COMMENT ON COLUMN withdrawals.user_id IS 'ID do usuário proprietário da retirada';
