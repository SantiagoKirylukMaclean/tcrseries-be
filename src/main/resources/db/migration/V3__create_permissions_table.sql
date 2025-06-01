-- Create permissions table
CREATE TABLE permissions (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id),
    access VARCHAR(50) NOT NULL,
    UNIQUE(user_id, access)
);

-- Create index for better performance
CREATE INDEX idx_permissions_user_id ON permissions(user_id);