ALTER TABLE expenses ADD COLUMN active BOOLEAN NOT NULL DEFAULT TRUE;

CREATE INDEX idx_expenses_active ON expenses(active);
CREATE INDEX idx_expenses_user_active ON expenses(user_id, active);

COMMENT ON COLUMN expenses.active IS 'Indica se a despesa está ativa';
