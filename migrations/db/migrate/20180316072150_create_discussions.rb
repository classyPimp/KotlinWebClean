class CreateDiscussions < ActiveRecord::Migration[5.1]
  def change
    create_table :discussions do |t|
      t.string :topic
      t.references :discussable, polymorphic: true
      t.references :user
      t.timestamps
    end
  end
end
