class CreateUserToUserRoleLinks < ActiveRecord::Migration[5.1]
  def change
    create_table :user_to_user_role_links do |t|
      t.references :user, foreign_key: true
      t.references :user_role, foreign_key: true
      t.timestamps
    end
  end
end
