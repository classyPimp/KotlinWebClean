class CreateDiscussionMessages < ActiveRecord::Migration[5.1]
  def change
    create_table :discussion_messages do |t|
      t.references :discussion, foreign_key: true
      t.references :discussion_message, foreign_key: true
      t.references :user
      t.integer :nest_level
      t.string :text
      t.timestamps
    end
  end
end
