/**
 * Unit tests for the abstract class Piece.
 */
package piece;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.Board;
import chess.Cell;

/**
 * @author Ravishankar P. Joshi
 *
 */
class PieceTest
{
	private Piece piece;
	private Board board;
	@BeforeEach
	void setUp()
	{
		piece = null;
		board = new Board();
	}

	@AfterEach
	void tearDown()
	{
		piece = null;
		board = null;
	}


	/**
	 * Test method for concrete method
	 * piece.Piece.moveTo(chess.Cell, chess.Board).
	 */
	@Test
	void testMoveTo()
	{
		try
		{
			//Creating a new black rook at the cell a1.
			piece = new Rook(Board.Black, board.getCellAt(Board.rowMin, Board.colMin));
			Cell dest= board.getCellAt((char)(Board.rowMin+2), Board.colMin);

			assertEquals(true, piece.moveTo(dest, board),
					"rook must be able to move to cell a3 from a1.");

			assertEquals(null, board.colourAt(Board.rowMin, Board.colMin),
					"a1 cell must be empty now.");

			assertEquals(piece, dest.getPiece(),
					"rook must be on the dest cell now.");
			assertEquals(dest, piece.currentPos,
					"rook must be on the dest cell now.");

			assertEquals(14, piece.moves.size(),
					"rook must have 14 moves from cell a3.");

			ArrayList<Cell> moveslist = piece.moves;
			dest= board.getCellAt((char)(Board.rowMin+1), (char)(Board.colMin+1));
			assertEquals(false, piece.moveTo(dest, board),
					"rook must not be able to move b2 from a3.");
			assertEquals(moveslist, piece.moves,
					"the list of moves must not change.");
		}
		catch(Exception e)
		{
			System.out.println("Exception in testMoveTo() method");
		}
	}

	/**
	 * Test method for concrete method
	 * piece.Piece.canMoveTo(chess.Cell, chess.Board).
	 */
	@Test
	void testCanMoveTo1()
	{
		try
		{
			//Creating a new black rook at the cell a1.
			piece = new Rook(Board.Black, board.getCellAt(Board.rowMin, Board.colMin));
			assertEquals(null, piece.moves);
			//Make sure that initially moves list is empty,
			//and, on calling canMoveTo(), it fills the list.
			Boolean x = piece.canMoveTo(
					board.getCellAt(Board.rowMax, Board.colMin), board);
			assertEquals(14, piece.moves.size());
			assertEquals(true, x);
		}
		catch(Exception e)
		{
			System.out.println("Exception in testCanMoveTo1() method");
		}
	}

	@Test
	void testCanMoveTo2()
	{
		try
		{
			//Creating a new black rook at the cell a1.
			piece = new Rook(Board.White, board.getCellAt(Board.rowMin, Board.colMin));
			ArrayList<Cell> temp = piece.getAllMoves(board);
			//Make sure that canMoveTo() doesn't mess with
			//already filled list of moves.
			assertEquals(14, piece.moves.size());
			assertEquals(temp, piece.moves);
			Boolean y = piece.canMoveTo(
					board.getCellAt(Board.rowMax, Board.colMin), board);
			assertEquals(true, y);
			Boolean selfCell = piece.canMoveTo(
					board.getCellAt(Board.rowMin, Board.colMin), board);
			assertEquals(false, selfCell);
		}
		catch(Exception e)
		{
			System.out.println("Exception in testCanMoveTo2() method");
		}
	}

	/**
	 * Test method for concrete method
	 * piece.Piece.movesInDir(chess.Board, int, int).
	 * It should work independent of the type of piece.
	 */
	@Test
	void rookMovesFromCornerTest()
	{
		try
		{
			//Creating a new black rook at the cell a1.
			piece = new Rook(Board.Black, board.getCellAt(Board.rowMin, Board.colMin));
			ArrayList<Cell> temp;
			temp = piece.movesInDir(board, 1, 0);
			//It must be able to move only in the same column.
			for(Cell c: temp)
				assertEquals(Board.colMin, c.col);
			assertEquals(7, temp.size());
			assertFalse(temp.contains(board.getCellAt(Board.rowMin, Board.colMin)));

			temp = piece.movesInDir(board, -1, 0);
			//It can't go outside the board, by decrementing row from '1'.
			assertEquals(0, temp.size());

			temp = piece.movesInDir(board, 0, 1);
			//It must be able to move only in the same row.
			for(Cell c: temp)
				assertEquals(Board.rowMin, c.row);
			assertEquals(7, temp.size());
			assertFalse(temp.contains(board.getCellAt(Board.rowMin, Board.colMin)));

			temp = piece.movesInDir(board, 0, -1);
			//It can't go outside the board, by decrementing column from 'a'.
			assertEquals(0, temp.size());
		}
		catch(Exception e)
		{
			System.out.println("Exception in "
					+ "rookMovesFromCornerTest() method");
		}
	}

	/**
	 * Test method for concrete method
	 * piece.Piece.movesInDir(chess.Board, int, int).
	 * It should test the bishop moves along a constant difference diagonal.
	 */
	@Test
	void bishopMovesFromCornerDiffConstantTest()
	{
		try
		{
			//This tests moves of a bishop along a constant difference diagonal.
			//Creating a new white bishop at the cell a1.
			piece = new Bishop(Board.White, board.getCellAt(Board.rowMin, Board.colMin));
			ArrayList<Cell> temp;
			temp = piece.movesInDir(board, 1, 1);
			//It must be able to move only in the same diagonal.
			for(Cell c: temp)
				assertEquals(0, (c.col - Board.colMin)-(c.row - Board.rowMin));
			assertEquals(7, temp.size());
			assertFalse(temp.contains(board.getCellAt(Board.rowMin, Board.colMin)));

			temp = piece.movesInDir(board, -1, -1);
			//It can't go outside the board.
			assertEquals(0, temp.size());

			temp = piece.movesInDir(board, 1, -1);
			//It can't go outside the board.
			assertEquals(0, temp.size());

			temp = piece.movesInDir(board, -1, 1);
			//It can't go outside the board.
			assertEquals(0, temp.size());
		}
		catch(Exception e)
		{
			System.out.println("Exception in "
					+ "bishopMovesFromCornerDiffConstantTest() method");
		}
	}

	/**
	 * Test method for concrete method
	 * piece.Piece.movesInDir(chess.Board, int, int).
	 * It should test the bishop moves along a constant sum diagonal.
	 */
	@Test
	void bishopMovesFromCornerSumConstantTest()
	{
		try
		{
			//This tests moves of a bishop along a constant sum diagonal.
			//Creating a new white bishop at the cell a8.
			piece = new Bishop(Board.White, board.getCellAt(Board.rowMax, Board.colMin));
			ArrayList<Cell> temp;

			Cell edge = board.getCellAt(Board.rowMin, Board.colMax);
			new Pawn(Board.White, edge);
			//putting an intruding white pawn on h1.

			temp = piece.movesInDir(board, -1, 1);
			//It must be able to move only in the same diagonal.
			for(Cell c: temp)
				assertEquals(7, (c.col - Board.colMin)+(c.row - Board.rowMin));
			assertEquals(6, temp.size());
			assertFalse(temp.contains(board.getCellAt(Board.rowMin, Board.colMin)));
			assertFalse(temp.contains(edge), "A white bishop cannot attack a white pawn.");

			edge.setPiece(null);//destruct the old pawn.
			new Pawn(Board.Black, edge);
			//putting an intruding black pawn on h1.

			temp = piece.movesInDir(board, -1, 1);
			assertEquals(7, temp.size());
			assertFalse(temp.contains(board.getCellAt(Board.rowMin, Board.colMin)));
			assertTrue(temp.contains(edge), "A white bishop can attack a black pawn.");
		}
		catch(Exception e)
		{
			System.out.println("Exception in "
					+ "bishopMovesFromCornerSumConstantTest() method");
		}
	}

	/**
	 * Test method for {@link piece.Piece#getColour()}.
	 */
	@Test
	void testGetColour()
	{
		try
		{
			piece = new Bishop(Board.White, board.getCellAt(Board.rowMin, Board.colMin));
			//a white bishop at an edge cell is stored into a piece place-holder.
			assertEquals(Board.White, piece.getColour());

			piece = new Rook(Board.Black, board.getCellAt(Board.rowMax, Board.colMax));
			//a black rook at an edge cell is stored into a piece place-holder.
			assertEquals(Board.Black, piece.getColour());
		}
		catch(Exception e)
		{
			System.out.println("Exception in testGetColour() method");
		}
	}

}
