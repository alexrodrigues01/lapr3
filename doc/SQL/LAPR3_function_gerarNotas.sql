CREATE OR REPLACE FUNCTION fncGerarNotas(p_farmacia_recetora Farmacia.id%TYPE, p_farmacia_emissora Farmacia.id%TYPE, p_produto Produto.id%TYPE, p_quantidade_necessaria int) RETURN Nota.id%TYPE IS

    v_id_nota Nota.id%TYPE;

BEGIN

  -- obtem o id que vamos atribuir às notas que vamos criar
  SELECT MAX(id) INTO v_id_nota
  FROM Nota;

  IF v_id_nota IS NULL
  THEN
      v_id_nota := 0;
  ELSE
      v_id_nota := v_id_nota + 1;
  END IF;

  -- criação da nota de emissão
  INSERT INTO Nota (id, id_farmaciaEmissora, id_farmaciaRecetora, id_produto, quantidade, id_tipoNota) values (v_id_nota, p_farmacia_recetora, p_farmacia_emissora, p_produto, p_quantidade_necessaria, 2);

  -- desconta no stock da farmácia a quantidade que foi emitida
  UPDATE Produto
  SET stock = stock - p_quantidade_necessaria
  WHERE id = p_produto
    AND id_farmacia = p_farmacia_emissora;

  -- criação da nota de receção
  INSERT INTO Nota (id, id_farmaciaEmissora, id_farmaciaRecetora, id_produto, quantidade, id_tipoNota) values (v_id_nota +1, p_farmacia_recetora, p_farmacia_emissora, p_produto, p_quantidade_necessaria, 1);

  -- adiciona no stock da farmácia a quantidade que foi recebida
  UPDATE Produto
  SET stock = stock + p_quantidade_necessaria
  WHERE id = p_produto
    AND id_farmacia = p_farmacia_recetora;

  -- retorna o id da nota de emissão (o id da nota de recessão é logo o próximo número)
  RETURN v_id_nota;

END;
