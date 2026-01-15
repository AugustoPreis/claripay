ALTER TABLE withdrawals ADD COLUMN active BOOLEAN NOT NULL DEFAULT TRUE;

CREATE INDEX idx_withdrawals_active ON withdrawals(active);
CREATE INDEX idx_withdrawals_user_active ON withdrawals(user_id, active);

COMMENT ON COLUMN withdrawals.active IS 'Indica se a retirada está ativa';
