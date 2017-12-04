class CreateAccount < ActiveRecord::Migration[5.1]
  def change
    create_table :accounts do |t|
      t.string :password
      t.string :email
      t.references :user, foreign_key: true
      t.timestamps
    end
  end
end
